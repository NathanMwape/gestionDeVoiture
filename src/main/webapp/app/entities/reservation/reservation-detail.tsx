import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reservation.reducer';

export const ReservationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reservationEntity = useAppSelector(state => state.reservation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reservationDetailsHeading">
          <Translate contentKey="reservaionApp.reservation.detail.title">Reservation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{reservationEntity.id}</dd>
          <dt>
            <span id="dateDebut">
              <Translate contentKey="reservaionApp.reservation.dateDebut">Date Debut</Translate>
            </span>
          </dt>
          <dd>
            {reservationEntity.dateDebut ? (
              <TextFormat value={reservationEntity.dateDebut} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dateFin">
              <Translate contentKey="reservaionApp.reservation.dateFin">Date Fin</Translate>
            </span>
          </dt>
          <dd>
            {reservationEntity.dateFin ? <TextFormat value={reservationEntity.dateFin} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="montant">
              <Translate contentKey="reservaionApp.reservation.montant">Montant</Translate>
            </span>
          </dt>
          <dd>{reservationEntity.montant}</dd>
          <dt>
            <span id="statut">
              <Translate contentKey="reservaionApp.reservation.statut">Statut</Translate>
            </span>
          </dt>
          <dd>{reservationEntity.statut}</dd>
          <dt>
            <Translate contentKey="reservaionApp.reservation.utilisateur">Utilisateur</Translate>
          </dt>
          <dd>{reservationEntity.utilisateur ? reservationEntity.utilisateur.id : ''}</dd>
          <dt>
            <Translate contentKey="reservaionApp.reservation.voiture">Voiture</Translate>
          </dt>
          <dd>{reservationEntity.voiture ? reservationEntity.voiture.id : ''}</dd>
          <dt>
            <Translate contentKey="reservaionApp.reservation.gestionFlotte">Gestion Flotte</Translate>
          </dt>
          <dd>{reservationEntity.gestionFlotte ? reservationEntity.gestionFlotte.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/reservation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reservation/${reservationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReservationDetail;
