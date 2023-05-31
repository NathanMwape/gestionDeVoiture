import { IReservation } from 'app/shared/model/reservation.model';
import { IVoiture } from 'app/shared/model/voiture.model';

export interface IGestionFlotte {
  id?: number;
  reservations?: IReservation[] | null;
  voitures?: IVoiture[] | null;
}

export const defaultValue: Readonly<IGestionFlotte> = {};
