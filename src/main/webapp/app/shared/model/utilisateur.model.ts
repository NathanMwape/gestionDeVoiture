export interface IUtilisateur {
  id?: number;
  nom?: string | null;
  isAdmin?: boolean | null;
  email?: string | null;
  motDePasse?: string | null;
}

export const defaultValue: Readonly<IUtilisateur> = {
  isAdmin: false,
};
