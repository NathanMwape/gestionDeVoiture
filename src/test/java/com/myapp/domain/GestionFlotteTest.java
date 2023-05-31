package com.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GestionFlotteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GestionFlotte.class);
        GestionFlotte gestionFlotte1 = new GestionFlotte();
        gestionFlotte1.setId(1L);
        GestionFlotte gestionFlotte2 = new GestionFlotte();
        gestionFlotte2.setId(gestionFlotte1.getId());
        assertThat(gestionFlotte1).isEqualTo(gestionFlotte2);
        gestionFlotte2.setId(2L);
        assertThat(gestionFlotte1).isNotEqualTo(gestionFlotte2);
        gestionFlotte1.setId(null);
        assertThat(gestionFlotte1).isNotEqualTo(gestionFlotte2);
    }
}
