package org.esa.beam.waterradiance.realoptimizers;

import java.io.IOException;

class NnAtmoWat {

    // new nets, RD 20130308:
    private static final String rhopath_net_name = "ac_rhopath_b29/17x37x31_121.8.net";
    private static final String tdown_net_name = "t_down_b29/17x37x31_89.4.net";
    private static final String tup_net_name = "ac_tup_b29/17x37x31_83.8.net";

    private static final double DEG_2_RAD = (3.1415927 / 180.0);
    private static final int[] lam29_meris11_ix = {1, 2, 4, 6, 11, 12, 15, 20, 22, 24, 25};

    private final a_nn rhopath_net;
    private final a_nn tdown_net;
    private final a_nn tup_net;
    private final a_nn wat_net_for;
    private final AlphaTab alphaTab;

    NnAtmoWat(AlphaTab alphaTab) throws IOException {
        this.alphaTab = alphaTab;
        final NnResources nnResources = new NnResources();
        rhopath_net = LevMarNN.prepare_a_nn(nnResources.getAcForwardNetPath(rhopath_net_name));
        tdown_net = LevMarNN.prepare_a_nn(nnResources.getAcForwardNetPath(tdown_net_name));
        tup_net = LevMarNN.prepare_a_nn(nnResources.getAcForwardNetPath(tup_net_name));
        wat_net_for = LevMarNN.prepare_a_nn(nnResources.getNetWaterPath());
    }

    /**
     * atmosphere **
     * <p/>
     * conc_all: Input parameters
     * rtose_nn: Output values
     * m:        conc_all size
     * n:        size of ...
     * nn_data:  additional data
     */
     NNReturnData nn_atmo_wat(double[] conc_all, double[] rtosa_nn, int m, int n, s_nn_atdata nn_data) {
        int ilam, nlam, ix;

        double sun_thet, view_zeni, azi_diff_hl, temperature, salinity;
        double log_aot, log_ang, log_wind, log_agelb, log_apart, log_apig, log_bpart;
        double log_conc_chl, log_conc_det, log_conc_gelb, log_conc_min, log_conc_wit;

        double[] innet = new double[10];
        double[] outnet = new double[63];
        double[] tdown_nn = new double[29];
        double[] tup_nn = new double[29];
        double[] outnet1 = new double[29];
        double[] outnet2 = new double[29];
        double[] outnet3 = new double[29];
        double[] rlpath_nn = new double[29];
        double[] rw_2flow = new double[29];
        double[] conc_2flow = new double[5];
        double[] rlw_nn = new double[29];
        double[] rpath_nn = new double[29];
        double[] rw_nn = new double[29];
        int prepare;
        double x, y, z, radius, azimuth, elevation;

        //char *atm_net_name_for={"27x57x47_47.7.net"};
        //char *atm_net_name_for={"./for_21bands_20110918/27x57x67_59.2.net"};
        //char *atm_net_name_for={"./for_21bands_20110918/17_3978.7.net"};

        //char *rhopath_net_name={"./for_21bands_20120112/rhopath/27x17_18.2.net"};
        //char *tdown_net_name  ={"./for_21bands_20120112/tdown/27x17_202.6.net"};
        //char *tup_net_name    ={"./for_21bands_20120112/tup/27x17_181.6.net"};

        //char *rhopath_net_name={"./oc_cci_20120222/ac_forward_all/ac_rhopath_b29/27x27_32.7.net"};
        //char *tdown_net_name  ={"./oc_cci_20120222/ac_forward_all/t_down_b29/27x27_73.7.net"};
        //char *tup_net_name    ={"./oc_cci_20120222/ac_forward_all/ac_tup_b29/27x27_75.4.net"};


        s_nn_atdata nn_at_data = nn_data;

        nlam = n;

        prepare = nn_at_data.getPrepare();
        sun_thet = nn_at_data.getSun_thet();
        view_zeni = nn_at_data.getView_zeni();
        azi_diff_hl = nn_at_data.getAzi_diff_hl();
        //azi_diff_hl=180.0-azi_diff_hl;
        temperature = nn_at_data.getTemperature();
        salinity = nn_at_data.getSalinity();

        azimuth = DEG_2_RAD * azi_diff_hl;
        elevation = DEG_2_RAD * view_zeni;
        x = Math.sin(elevation) * Math.cos(azimuth);
        y = Math.sin(elevation) * Math.sin(azimuth);
        z = Math.cos(elevation);


        log_aot = conc_all[0];
        log_ang = conc_all[1];
        log_wind = conc_all[2];
//            log_conc_chl = conc_all[3];
//            log_conc_det = conc_all[4];
//            log_conc_gelb = conc_all[5];
//            log_conc_min = conc_all[6];
//            log_conc_wit = conc_all[7];

        // innet[0] = sun_thet;
        // CHANGED for new nets, RD 20130308:
        innet[0] = Math.cos(DEG_2_RAD * sun_thet);

        innet[1] = x;
        innet[2] = y;
        innet[3] = z;

        //innet[4] = log_aot;
        //innet[5] = log_ang;
        //innet[6] = log_wind;

        // CHANGED for new nets, RD 20130308:
        innet[4] = Math.exp(log_aot);
        innet[5] = Math.exp(log_ang);
        innet[6] = Math.exp(log_wind);

        innet[7] = temperature;
        innet[8] = salinity;

        outnet1 = LevMarNN.use_the_nn(rhopath_net, innet, outnet1, alphaTab);
        outnet2 = LevMarNN.use_the_nn(tdown_net, innet, outnet2, alphaTab);
        outnet3 = LevMarNN.use_the_nn(tup_net, innet, outnet3, alphaTab);

        nlam = n; // if n == 11, then iteration for LM fit, if > 11, then computation for full spectrum
        if (nlam == 11) {
            for (ilam = 0; ilam < nlam; ilam++) {
                ix = lam29_meris11_ix[ilam];
                rpath_nn[ilam] = outnet1[ix];
                tdown_nn[ilam] = outnet2[ix];
                tup_nn[ilam] = outnet3[ix];
            }
            final NNReturnData res = LevMarNN.nn_water(conc_all, rlw_nn, m, n, nn_at_data, wat_net_for, alphaTab);
            rlw_nn = res.getOutputValues();
            nn_at_data = res.getNn_atdata();
            for (ilam = 0; ilam < 11; ilam++) {
                rw_nn[ilam] = rlw_nn[ilam];//M_PI;
                rtosa_nn[ilam] = rpath_nn[ilam] + rw_nn[ilam] * tdown_nn[ilam] * tup_nn[ilam];
            }
        } else {
            nlam = 29; // all bands for other calculations
            for (ilam = 0; ilam < nlam; ilam++) {
                rpath_nn[ilam] = outnet1[ilam];
                tdown_nn[ilam] = outnet2[ilam];
                tup_nn[ilam] = outnet3[ilam];
            }
            n = nlam;
            final NNReturnData res = LevMarNN.nn_water(conc_all, rlw_nn, m, n, nn_data, wat_net_for, alphaTab);
            rlw_nn = res.getOutputValues();
            nn_at_data = res.getNn_atdata();
            for (ilam = 0; ilam < nlam; ilam++) {
                rw_nn[ilam] = rlw_nn[ilam];//*M_PI;// ! with pi included, 21 bands
                rtosa_nn[ilam] = rpath_nn[ilam] + rw_nn[ilam] * tdown_nn[ilam] * tup_nn[ilam];
                nn_at_data.setTdown_nn(ilam, tdown_nn[ilam]);
                nn_at_data.setTup_nn(ilam, tup_nn[ilam]);
                nn_at_data.setRpath_nn(ilam, rpath_nn[ilam]);
                nn_at_data.setRw_nn(ilam, rw_nn[ilam]);
            }
        }
        return new NNReturnData(rtosa_nn, nn_data);
    }
}