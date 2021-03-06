/*
 * Copyright (C) 2012 Brockmann Consult GmbH (info@brockmann-consult.de)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see http://www.gnu.org/licenses/
 */

package org.esa.beam.waterradiance;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author Norman Fomferra
 */
public class LevMarNnLibTest {

    private static final LevMarNnLib lib;

    private static final double[] SUN_SPECTRAL_FLUXES_NORMAN = {
            1773.03,
            1942.73,
            1993.86,
            1994.48,
            1864.34,
            1706.86,
            1583.67,
            1522.19,
            1455.78,
            1309.06,
            1297.21,
            1217.26,
            990.95,
            961.43,
            925.89
    };

    private static final double[] SUN_SPECTRAL_FLUXES_20060116 = {
            1773.7241,
            1943.4926,
            1994.6368,
            1995.2565,
            1865.0692,
            1707.5303,
            1584.2865,
            1522.7838,
            1456.3502,
            1309.5717,
            1297.716,
            1217.7358,
            991.3362,
            961.8075,
            926.2471
    };

    static {
        WaterRadianceOperator.installAuxdataAndLibrary();
        lib = LevMarNnLib.INSTANCE;

        final double[] input = assembleInput(38.532475,
                142.5679,
                23.14311,
                103.322136,
                7.8687496,
                -2.525,
                1017.61487,
                317.83008,
                20.0,
                12.0,
                new double[]{70.43595,
                        60.354992,
                        44.56492,
                        39.043613,
                        27.241674,
                        15.729385,
                        11.943042,
                        10.802422,
                        8.662219,
                        6.4378233,
                        2.8837085,
                        5.4307566,
                        3.2948744,
                        3.0640657,
                        2.1505015},
                SUN_SPECTRAL_FLUXES_NORMAN);
        final double[] output = new double[75];
        final double[] debug_dat = new double[100];

        lib.levmar_nn(181, input, input.length, output, output.length, debug_dat);
    }


    private static double[] expected_Norman = new double[]{0.05106093416816374, 0.04016991778889496, 0.02909920769222207, 0.02572070343095661, 0.02011765458709306, 0.012768282032716158, 0.010003103221994807, 0.009281329685685624, 0.00797110510189507, 0.006325542223074678, 0.005709368943107607, 0.004271587237326763, 0.1605765859479735, 0.12347101911454453, 0.08386829382085119, 0.07207724680501985, 0.050667201656415695, 0.0347234929189706, 0.02691333011992756, 0.024631695524768682, 0.02139496440081754, 0.017275548489755516, 0.015433839353211149, 0.010958260739556632, 0.001421427165804695, 0.003061217636211855, 0.008143958538004321, 0.01051046123089201, 0.015445670858853743, 0.0060517632821759145, 0.0036787581550209603, 0.00329481984265189, 0.0018966022060828572, 5.687023475468726E-4, 5.920284440558227E-4, 2.494818412494278E-4, 0.8344168623472699, 0.8699249334867316, 0.9089271255801348, 0.9210074116845552, 0.943324453079678, 0.9603645672146746, 0.9689648597354495, 0.971469693841055, 0.9750239478976463, 0.9796966571978689, 0.9818455881478028, 0.9870074651635823, 0.8481458076656145, 0.8814655139484314, 0.9177373577404033, 0.928799939589665, 0.9492161773985954, 0.9647897033513108, 0.9725108001758349, 0.9747490951945208, 0.9780125061868381, 0.9822092635511986, 0.9840644845437744, 0.988668427120618, 0.035343272734836166, 2.030822312785605, 0.022377158448657692, 0.043892880789474814, 0.6808143590840406, 0.8076664967266487, 0.051782921860854876, 2.120160919879232E-5, 12.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private static double[] expected_20060116_ocean = new double[]{0.06674266859987914, 0.05270575492147841, 0.037690366457760964, 0.03184916519569875, 0.021960431891169183, 0.014177790616298265, 0.011078825564823104, 0.010191746071164211, 0.008881982756463491, 0.007173714204852539, 0.0063869638297191595, 0.004658747017442468, 0.19574706012342358, 0.15157018386536142, 0.10352631516102018, 0.08909313800779708, 0.06288944058796991, 0.04342353831958465, 0.03392046239869853, 0.031213821727402044, 0.02732892863115674, 0.02236899821573684, 0.02007810424321907, 0.014579158903760553, 0.0202935336616015, 0.019313868657338175, 0.018231467155049294, 0.012746154397672103, 0.007397090413090268, 0.0013544182605059593, 7.669167624948831E-4, 6.782236081264426E-4, 4.26195857617963E-4, 1.442906127842104E-4, 1.4838524337338385E-4, 6.024894345090439E-5, 0.8180314509941471, 0.8555729435453423, 0.8971489067219438, 0.9101108238728723, 0.9342600735467045, 0.9529614832249118, 0.9624545849462094, 0.9652388541741974, 0.9693292346923181, 0.974628964036103, 0.9770044932494764, 0.9827835056850729, 0.8281479427589371, 0.8644482752003646, 0.9043599135980152, 0.916688398527084, 0.9396481919620081, 0.9573069648127139, 0.9661882416710248, 0.9687343971127396, 0.9725207052436183, 0.9773888098215233, 0.9795525777510199, 0.9847796648538913, 0.060334779880829074, 1.8036525938901975, 0.012137569774817959, 0.02957600007559519, 0.0026176252991524786, 0.1685809732536486, 2.9684068563415116E-4, 4.782465803972643E-7, 13.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private static double[] expected_20060116_cloud = new double[]{0.3026792354396823, 0.30184442063205946, 0.3020461562031379, 0.3007758486396829, 0.3010356555643136, 0.3005364530236857, 0.3007750118065772, 0.30027829659637095, 0.29883401372349083, 0.2988576437013362, 0.2653722559834979, 0.2945622457458439, 0.2911376394881384, 0.2570449115593434, 0.21690455662318547, 0.20378611318169831, 0.1792790417422351, 0.15893654225445544, 0.1486940882783402, 0.14610282330906016, 0.1426538210714906, 0.13971427328072725, 0.13895578041847229, 0.1393085088947425, 0.7137900568734508, 0.690855142999315, 0.6078800234450018, 0.44861975618647526, 0.28667472828787965, 0.07130010813625627, 0.05107570894284326, 0.049839986694850145, 0.021952840631883595, 0.005245718600579985, 0.00958684500782857, 0.003051126821855082, 0.6971610186673333, 0.7280517433483629, 0.7654077550965108, 0.7774950205210929, 0.801974231275719, 0.8219950826291227, 0.8335938393545943, 0.8363793339695391, 0.8390843263286634, 0.8420647109575554, 0.8435397798280604, 0.8428652057154079, 0.7327177353042682, 0.7633094749826441, 0.7997547089302903, 0.8112508765095435, 0.834566891174742, 0.8540327835092385, 0.8646331426427571, 0.86716742855865, 0.8690166991314628, 0.8703800328878555, 0.8706683396037471, 0.8683045801954611, 1.0, 0.0223707718561656, 4.50370585246299E-6, 1.2923579121829584E-7, 3.4869564558253436E-8, 28.92060269302259, 4.302179301528751E-7, 4.2968647934537865, 150.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private static double[] expected_20060116_land = new double[]{0.05940486599705885, 0.04834449806498461, 0.03641968459789665, 0.033941544264534246, 0.035856072733429424, 0.025940122404248298, 0.020817244287082558, 0.01996426189636208, 0.04440945866001555, 0.09507140109908682, 0.09938762634020118, 0.1088207950157593, 0.2619214953734951, 0.22579444711956842, 0.18308281316245156, 0.1692544302497904, 0.14308629306417303, 0.12198610348201357, 0.1112655946308468, 0.10833665591325814, 0.10476365837052481, 0.10102457772210402, 0.09962014763958081, 0.09831334868715963, 0.004620976705367527, 0.007855395755515835, 0.015807196507996488, 0.019725175013204102, 0.03088083376246824, 0.04781866575393196, 0.04899299192319983, 0.057500108566800316, 0.09027942577338044, 0.04781513924102927, 0.05640461693187578, 0.024986108780668616, 0.6845288795568357, 0.7174804615006163, 0.7576444886501825, 0.7706981450338162, 0.7968808446273524, 0.8183807638426794, 0.8306117584645453, 0.8336514560597317, 0.8369195522277255, 0.8404865955694074, 0.8421532867604298, 0.8427893919576518, 0.7591727491081108, 0.789747198782401, 0.8263968304514654, 0.8380700101160066, 0.860564231600695, 0.8792248656193214, 0.8892683458508261, 0.8917989480341746, 0.8938912294705383, 0.8957948575240698, 0.8962974956774261, 0.8949283322349921, 0.7911494622641971, 0.0223707718561656, 1.98377183553716, 9.944304746421146, 1.3019086327465452, 0.020299056956487198, 99.38488105187314, 0.14565318291219378, 86.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private static double[] expected_20060116_subset_ocean = new double[]{0.057672590497990096, 0.04572532386883093, 0.03171267837750812, 0.02564602280962399, 0.017664553648440915, 0.011981326776316074, 0.009699751775363648, 0.00904915427541893, 0.00817508445561847, 0.006887354852911231, 0.006331618722387516, 0.005084252164038501, 0.1557502921717927, 0.12125723285196646, 0.08409218604965063, 0.07298600840205642, 0.05271094129550316, 0.03756712280924682, 0.030177310655138245, 0.028070982450512483, 0.025106893142537133, 0.021405181461766405, 0.019735198778306397, 0.016138479805418188, 0.036704503446886964, 0.029945086654068684, 0.019019836422889166, 0.008990121634280096, 0.003401323763599041, 4.913437968390986E-4, 2.891450682035031E-4, 2.596704397289908E-4, 1.7611746902150717E-4, 6.0314599412042874E-5, 6.186503163828636E-5, 2.595240543075126E-5, 0.8179220081184102, 0.8537969693341374, 0.8932863316930522, 0.9056527548951594, 0.928853964086461, 0.947055867343571, 0.9563758107243733, 0.9591320185058001, 0.9631748932941109, 0.9684537633406536, 0.9707655542201157, 0.9762245822129659, 0.8457289190109482, 0.8779050120412504, 0.9128086080384664, 0.9235154988210121, 0.9434417852173793, 0.9587724465746792, 0.9664951259174992, 0.9686837582759211, 0.9719603453547602, 0.9761284866905711, 0.9779531073812645, 0.9822363504916153, 0.1362329512244428, 1.5147092145278152, 0.0031091743928056536, 0.0028818090214242395, 0.0027967049608213323, 0.048879257477057535, 4.34881324533195E-4, 4.3342768514671283E-7, 15.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

    @Test
    public void testL1b_testDataNorman() throws Exception {
        final double solar_zenith = 38.532475;
        final double solar_azimuth = 142.5679;
        final double view_zenith = 23.14311;
        final double view_azimuth = 103.322136;
        final double wind_x = 7.8687496;
        final double wind_y = -2.525;
        final double surf_pressure = 1017.61487;
        final double ozone = 317.83008;
        final double temperature = 20.0;
        final double salinity = 12.0;

        final double[] toa_radiances = {
                70.43595,
                60.354992,
                44.56492,
                39.043613,
                27.241674,
                15.729385,
                11.943042,
                10.802422,
                8.662219,
                6.4378233,
                2.8837085,
                5.4307566,
                3.2948744,
                3.0640657,
                2.1505015
        };

        final double[] input = assembleInput(solar_zenith,
                solar_azimuth,
                view_zenith,
                view_azimuth,
                wind_x,
                wind_y,
                surf_pressure,
                ozone,
                temperature,
                salinity,
                toa_radiances,
                SUN_SPECTRAL_FLUXES_NORMAN);
        final double[] output = new double[75];
        final double[] debug_dat = new double[100];

        int result = lib.levmar_nn(181, input, input.length, output, output.length, debug_dat);
        assertEquals(0, result);
        assertArrayEquals(expected_Norman, output, 1e-8);
    }

    @Test
    public void testLib_MER_RR__1PRACR20060116_201233_000026092044_00200_20294_0000_ocean() throws Exception {
        final double solar_zenith = 43.913773;
        final double solar_azimuth = 76.12128;
        final double view_zenith = 34.740032;
        final double view_azimuth = 108.32797;
        final double wind_x = 5.7124996;
        final double wind_y = 2.25625;
        final double surf_pressure = 1021.8688;
        final double ozone = 277.4056;
        final double temperature = 18.54;
        final double salinity = 35.34;

        final double[] toa_radiances = {
                84.99878,
                73.27003,
                53.322674,
                44.631977,
                27.512224,
                16.187922,
                12.244357,
                10.961995,
                9.097538,
                6.74975,
                3.1055324,
                5.605122,
                3.3304179,
                3.0823767,
                2.4926267
        };

        final double[] input = assembleInput(solar_zenith,
                solar_azimuth,
                view_zenith,
                view_azimuth,
                wind_x,
                wind_y,
                surf_pressure,
                ozone,
                temperature,
                salinity,
                toa_radiances,
                SUN_SPECTRAL_FLUXES_20060116);

        final double[] output = new double[75];
        final double[] debug_dat = new double[100];

        int result = lib.levmar_nn(873, input, input.length, output, output.length, debug_dat);
        assertEquals(0, result);
        assertArrayEquals(expected_20060116_ocean, output, 1e-8);
    }

    @Test
    public void testLib_MER_RR__1PRACR20060116_201233_000026092044_00200_20294_0000_cloud() throws Exception {
        final double solar_zenith = 42.372684;
        final double solar_azimuth = 128.2194;
        final double view_zenith = 23.510494;
        final double view_azimuth = 102.27474;
        final double wind_x = -9.75;
        final double wind_y = 0.6750001;
        final double surf_pressure = 1009.8656;
        final double ozone = 237.87592;
        final double temperature = 27.4855;
        final double salinity = 34.962;

        final double[] toa_radiances = {
                398.09485,
                434.67896,
                440.62158,
                433.41943,
                390.89935,
                356.09332,
                342.04077,
                331.19794,
                312.51495,
                287.70917,
                135.00192,
                237.70004,
                215.58147,
                205.36565,
                164.44276
        };

        final double[] input = assembleInput(solar_zenith,
                solar_azimuth,
                view_zenith,
                view_azimuth,
                wind_x,
                wind_y,
                surf_pressure,
                ozone,
                temperature,
                salinity,
                toa_radiances,
                SUN_SPECTRAL_FLUXES_20060116);

        final double[] output = new double[75];
        final double[] debug_dat = new double[100];

        int result = lib.levmar_nn(748, input, input.length, output, output.length, debug_dat);
        assertEquals(0, result);
        assertArrayEquals(expected_20060116_cloud, output, 1e-8);
    }

    @Test
    public void testLib_MER_RR__1PRACR20060116_201233_000026092044_00200_20294_0000_land() throws Exception {
        final double solar_zenith = 50.82132;
        final double solar_azimuth = 140.0397;
        final double view_zenith = 21.953356;
        final double view_azimuth = 101.89219;
        final double wind_x = -4.1289067;
        final double wind_y = -1.8664063;
        final double surf_pressure = 1018.4313;
        final double ozone = 231.5093;
        final double temperature = 24.5196;
        final double salinity = 34.808;

        final double[] toa_radiances = {
                66.59864,
                59.22042,
                45.29473,
                41.82709,
                39.72433,
                26.201996,
                20.222364,
                18.84353,
                38.00525,
                78.18966,
                23.406841,
                76.28124,
                67.89858,
                66.15208,
                40.435944
        };

        final double[] input = assembleInput(solar_zenith,
                solar_azimuth,
                view_zenith,
                view_azimuth,
                wind_x,
                wind_y,
                surf_pressure,
                ozone,
                temperature,
                salinity,
                toa_radiances,
                SUN_SPECTRAL_FLUXES_20060116);

        final double[] output = new double[75];
        final double[] debug_dat = new double[100];

        int result = lib.levmar_nn(723, input, input.length, output, output.length, debug_dat);
        assertEquals(0, result);
        assertArrayEquals(expected_20060116_land, output, 1e-8);
    }

    @Test
    public void testLib_MER_RR__1PRACR20060116_201233_000026092044_00200_20294_0000_subset_ocean() throws Exception {
        final double solar_zenith = 39.14653;
        final double solar_azimuth = 75.055664;
        final double view_zenith = 9.815097;
        final double view_azimuth = 104.866035;
        final double wind_x = -2.06875;
        final double wind_y = 5.0;
        final double surf_pressure = 1021.28125;
        final double ozone = 258.8853;
        final double temperature = 18.283518;
        final double salinity = 35.010246;

        final double[] toa_radiances = {
                79.37073,
                68.774155,
                48.480423,
                38.893734,
                24.135004,
                14.9269495,
                11.607487,
                10.517964,
                8.971358,
                6.9750304,
                3.0966594,
                5.982914,
                3.9026668,
                3.6500223,
                2.8130295
        };


        final double[] input = assembleInput(solar_zenith,
                solar_azimuth,
                view_zenith,
                view_azimuth,
                wind_x,
                wind_y,
                surf_pressure,
                ozone,
                temperature,
                salinity,
                toa_radiances,
                SUN_SPECTRAL_FLUXES_20060116);

        final double[] output = new double[75];
        final double[] debug_dat = new double[100];

        int result = lib.levmar_nn(583, input, input.length, output, output.length, debug_dat);
        assertEquals(0, result);
        assertArrayEquals(expected_20060116_subset_ocean, output, 1e-8);
    }

    private static double[] assembleInput(double solar_zenith,
                                          double solar_azimuth,
                                          double view_zenith,
                                          double view_azimuth,
                                          double wind_x,
                                          double wind_y,
                                          double surf_pressure,
                                          double ozone,
                                          double temperature,
                                          double salinity,
                                          double[] toa_radiances,
                                          double[] sun_spectral_fluxes) {
        final double[] input = new double[40];
        input[0] = solar_zenith;
        input[1] = solar_azimuth;
        input[2] = view_zenith;
        input[3] = view_azimuth;
        input[4] = surf_pressure;
        input[5] = ozone;
        input[6] = wind_x;
        input[7] = wind_y;
        input[8] = temperature;
        input[9] = salinity;
        for (int i = 0; i < 15; i++) {
            input[i + 10] = toa_radiances[i];
            input[i + 25] = sun_spectral_fluxes[i];
        }
        return input;
    }
}
