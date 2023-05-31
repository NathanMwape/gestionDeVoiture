import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import GestionFlotte from './gestion-flotte';
import GestionFlotteDetail from './gestion-flotte-detail';
import GestionFlotteUpdate from './gestion-flotte-update';
import GestionFlotteDeleteDialog from './gestion-flotte-delete-dialog';

const GestionFlotteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<GestionFlotte />} />
    <Route path="new" element={<GestionFlotteUpdate />} />
    <Route path=":id">
      <Route index element={<GestionFlotteDetail />} />
      <Route path="edit" element={<GestionFlotteUpdate />} />
      <Route path="delete" element={<GestionFlotteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GestionFlotteRoutes;
