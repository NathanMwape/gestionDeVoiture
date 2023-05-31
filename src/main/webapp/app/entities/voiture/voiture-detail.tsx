import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './voiture.reducer';

export const VoitureDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const voitureEntity = useAppSelector(state => state.voiture.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="voitureDetailsHeading">
          <Translate contentKey="reservaionApp.voiture.detail.title">Voiture</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{voitureEntity.id}</dd>
          <dt>
            <span id="marque">
              <Translate contentKey="reservaionApp.voiture.marque">Marque</Translate>
            </span>
          </dt>
          <dd>{voitureEntity.marque}</dd>
          <dt>
            <span id="modele">
              <Translate contentKey="reservaionApp.voiture.modele">Modele</Translate>
            </span>
          </dt>
          <dd>{voitureEntity.modele}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="reservaionApp.voiture.description">Description</Translate>
            </span>
          </dt>
          <dd>{voitureEntity.description}</dd>
          <dt>
            <span id="disponibilite">
              <Translate contentKey="reservaionApp.voiture.disponibilite">Disponibilite</Translate>
            </span>
          </dt>
          <dd>{voitureEntity.disponibilite ? 'true' : 'false'}</dd>
          <dt>
            <span id="tarifJournalier">
              <Translate contentKey="reservaionApp.voiture.tarifJournalier">Tarif Journalier</Translate>
            </span>
          </dt>
          <dd>{voitureEntity.tarifJournalier}</dd>
          <dt>
            <Translate contentKey="reservaionApp.voiture.gestionFlotte">Gestion Flotte</Translate>
          </dt>
          <dd>{voitureEntity.gestionFlotte ? voitureEntity.gestionFlotte.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/voiture" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/voiture/${voitureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VoitureDetail;
