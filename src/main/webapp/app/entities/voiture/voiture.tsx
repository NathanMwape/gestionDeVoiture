import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVoiture } from 'app/shared/model/voiture.model';
import { getEntities } from './voiture.reducer';

export const Voiture = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const voitureList = useAppSelector(state => state.voiture.entities);
  const loading = useAppSelector(state => state.voiture.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="voiture-heading" data-cy="VoitureHeading">
        <Translate contentKey="reservaionApp.voiture.home.title">Voitures</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="reservaionApp.voiture.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/voiture/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="reservaionApp.voiture.home.createLabel">Create new Voiture</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {voitureList && voitureList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="reservaionApp.voiture.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="reservaionApp.voiture.marque">Marque</Translate>
                </th>
                <th>
                  <Translate contentKey="reservaionApp.voiture.modele">Modele</Translate>
                </th>
                <th>
                  <Translate contentKey="reservaionApp.voiture.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="reservaionApp.voiture.disponibilite">Disponibilite</Translate>
                </th>
                <th>
                  <Translate contentKey="reservaionApp.voiture.tarifJournalier">Tarif Journalier</Translate>
                </th>
                <th>
                  <Translate contentKey="reservaionApp.voiture.gestionFlotte">Gestion Flotte</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {voitureList.map((voiture, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/voiture/${voiture.id}`} color="link" size="sm">
                      {voiture.id}
                    </Button>
                  </td>
                  <td>{voiture.marque}</td>
                  <td>{voiture.modele}</td>
                  <td>{voiture.description}</td>
                  <td>{voiture.disponibilite ? 'true' : 'false'}</td>
                  <td>{voiture.tarifJournalier}</td>
                  <td>
                    {voiture.gestionFlotte ? (
                      <Link to={`/gestion-flotte/${voiture.gestionFlotte.id}`}>{voiture.gestionFlotte.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/voiture/${voiture.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/voiture/${voiture.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/voiture/${voiture.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="reservaionApp.voiture.home.notFound">No Voitures found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Voiture;
