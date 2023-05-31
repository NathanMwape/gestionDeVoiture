import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IReservation } from 'app/shared/model/reservation.model';
import { getEntities } from './reservation.reducer';

export const Reservation = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const reservationList = useAppSelector(state => state.reservation.entities);
  const loading = useAppSelector(state => state.reservation.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="reservation-heading" data-cy="ReservationHeading">
        <Translate contentKey="reservaionApp.reservation.home.title">Reservations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="reservaionApp.reservation.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/reservation/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="reservaionApp.reservation.home.createLabel">Create new Reservation</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {reservationList && reservationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="reservaionApp.reservation.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="reservaionApp.reservation.dateDebut">Date Debut</Translate>
                </th>
                <th>
                  <Translate contentKey="reservaionApp.reservation.dateFin">Date Fin</Translate>
                </th>
                <th>
                  <Translate contentKey="reservaionApp.reservation.montant">Montant</Translate>
                </th>
                <th>
                  <Translate contentKey="reservaionApp.reservation.statut">Statut</Translate>
                </th>
                <th>
                  <Translate contentKey="reservaionApp.reservation.utilisateur">Utilisateur</Translate>
                </th>
                <th>
                  <Translate contentKey="reservaionApp.reservation.voiture">Voiture</Translate>
                </th>
                <th>
                  <Translate contentKey="reservaionApp.reservation.gestionFlotte">Gestion Flotte</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {reservationList.map((reservation, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/reservation/${reservation.id}`} color="link" size="sm">
                      {reservation.id}
                    </Button>
                  </td>
                  <td>
                    {reservation.dateDebut ? <TextFormat type="date" value={reservation.dateDebut} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {reservation.dateFin ? <TextFormat type="date" value={reservation.dateFin} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{reservation.montant}</td>
                  <td>{reservation.statut}</td>
                  <td>
                    {reservation.utilisateur ? (
                      <Link to={`/utilisateur/${reservation.utilisateur.id}`}>{reservation.utilisateur.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{reservation.voiture ? <Link to={`/voiture/${reservation.voiture.id}`}>{reservation.voiture.id}</Link> : ''}</td>
                  <td>
                    {reservation.gestionFlotte ? (
                      <Link to={`/gestion-flotte/${reservation.gestionFlotte.id}`}>{reservation.gestionFlotte.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/reservation/${reservation.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/reservation/${reservation.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/reservation/${reservation.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="reservaionApp.reservation.home.notFound">No Reservations found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Reservation;
