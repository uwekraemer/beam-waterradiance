package org.esa.beam.waterradiance.realoptimizers;


import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class NnResourcesTest {

    private NnResources nnResources;

    @Before
    public void setUp() {
        nnResources = new NnResources();
    }

    @Test
    public void testGetNormNetPath() {
        final String normNetPath = nnResources.getNormNetPath();
        assertNotNull(normNetPath);
        assertTrue(normNetPath.contains("27x41x27_23.3.net"));
        assertTrue(new File(normNetPath).isFile());
    }

    @Test
    public void testGetNetWaterPath() {
        final String netWaterPath = nnResources.getNetWaterPath();
        assertNotNull(netWaterPath);
        assertTrue(netWaterPath.contains("37x17_754.1.net"));
        assertTrue(new File(netWaterPath).isFile());
    }

    @Test
    public void testGetCentralWavelengthFRPath() {
        final String centralWlPath = nnResources.getCentralWavelengthFrPath();
        assertNotNull(centralWlPath);
        assertTrue(centralWlPath.contains("central_wavelen_fr.txt"));
        assertTrue(new File(centralWlPath).isFile());
    }

    @Test
    public void testGetCentralWavelengthRRPath() {
        final String centralWlPath = nnResources.getCentralWavelengthRrPath();
        assertNotNull(centralWlPath);
        assertTrue(centralWlPath.contains("central_wavelen_rr.txt"));
        assertTrue(new File(centralWlPath).isFile());
    }

    @Test
    public void testGetSunSpectralFluxFRPath() {
        final String sunSpectralFluxFrPath = nnResources.getSunSpectralFluxFrPath();
        assertNotNull(sunSpectralFluxFrPath);
        assertTrue(sunSpectralFluxFrPath.contains("sun_spectral_flux_fr.txt"));
        assertTrue(new File(sunSpectralFluxFrPath).isFile());
    }

    @Test
    public void testGetSunSpectralFluxRRPath() {
        final String sunSpectralFluxRrPath = nnResources.getSunSpectralFluxRrPath();
        assertNotNull(sunSpectralFluxRrPath);
        assertTrue(sunSpectralFluxRrPath.contains("sun_spectral_flux_rr.txt"));
        assertTrue(new File(sunSpectralFluxRrPath).isFile());
    }

    @Test
    public void testGetNominalLamSunPath() {
        final String nominalLamSunPath = nnResources.getNominalLamSunPath();
        assertNotNull(nominalLamSunPath);
        assertTrue(nominalLamSunPath.contains("nominal_lam_sun.txt"));
        assertTrue(new File(nominalLamSunPath).isFile());
    }

    @Test
    public void testGetAcForwardNetPath() {
        final String acForwardNetPath = nnResources.getAcForwardNetPath("ac_rhopath_b29/17x37x31_121.8.net");
        assertNotNull(acForwardNetPath);
        assertTrue(acForwardNetPath.contains("ac_rhopath_b29/17x37x31_121.8.net"));
        assertTrue(new File(acForwardNetPath).isFile());
    }
}
