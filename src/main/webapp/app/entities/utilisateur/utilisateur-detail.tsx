import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './utilisateur.reducer';

export const UtilisateurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const utilisateurEntity = useAppSelector(state => state.utilisateur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="utilisateurDetailsHeading">
          <Translate contentKey="reservaionApp.utilisateur.detail.title">Utilisateur</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="reservaionApp.utilisateur.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.nom}</dd>
          <dt>
            <span id="isAdmin">
              <Translate contentKey="reservaionApp.utilisateur.isAdmin">Is Admin</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.isAdmin ? 'true' : 'false'}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="reservaionApp.utilisateur.email">Email</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.email}</dd>
          <dt>
            <span id="motDePasse">
              <Translate contentKey="reservaionApp.utilisateur.motDePasse">Mot De Passe</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.motDePasse}</dd>
        </dl>
        <Button tag={Link} to="/utilisateur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/utilisateur/${utilisateurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UtilisateurDetail;
