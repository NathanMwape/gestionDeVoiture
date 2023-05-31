import utilisateur from 'app/entities/utilisateur/utilisateur.reducer';
import voiture from 'app/entities/voiture/voiture.reducer';
import reservation from 'app/entities/reservation/reservation.reducer';
import gestionFlotte from 'app/entities/gestion-flotte/gestion-flotte.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  utilisateur,
  voiture,
  reservation,
  gestionFlotte,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
