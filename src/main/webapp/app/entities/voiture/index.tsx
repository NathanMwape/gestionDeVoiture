import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Voiture from './voiture';
import VoitureDetail from './voiture-detail';
import VoitureUpdate from './voiture-update';
import VoitureDeleteDialog from './voiture-delete-dialog';

const VoitureRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Voiture />} />
    <Route path="new" element={<VoitureUpdate />} />
    <Route path=":id">
      <Route index element={<VoitureDetail />} />
      <Route path="edit" element={<VoitureUpdate />} />
      <Route path="delete" element={<VoitureDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VoitureRoutes;
