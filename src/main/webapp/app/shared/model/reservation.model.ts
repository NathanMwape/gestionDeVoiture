import dayjs from 'dayjs';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { IVoiture } from 'app/shared/model/voiture.model';
import { IGestionFlotte } from 'app/shared/model/gestion-flotte.model';

export interface IReservation {
  id?: number;
  dateDebut?: string | null;
  dateFin?: string | null;
  montant?: number | null;
  statut?: string | null;
  utilisateur?: IUtilisateur | null;
  voiture?: IVoiture | null;
  gestionFlotte?: IGestionFlotte | null;
}

export const defaultValue: Readonly<IReservation> = {};
