import { IGestionFlotte } from 'app/shared/model/gestion-flotte.model';

export interface IVoiture {
  id?: number;
  marque?: string | null;
  modele?: string | null;
  description?: string | null;
  disponibilite?: boolean | null;
  tarifJournalier?: number | null;
  gestionFlotte?: IGestionFlotte | null;
}

export const defaultValue: Readonly<IVoiture> = {
  disponibilite: false,
};
