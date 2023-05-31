import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGestionFlotte } from 'app/shared/model/gestion-flotte.model';
import { getEntities as getGestionFlottes } from 'app/entities/gestion-flotte/gestion-flotte.reducer';
import { IVoiture } from 'app/shared/model/voiture.model';
import { getEntity, updateEntity, createEntity, reset } from './voiture.reducer';

export const VoitureUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const gestionFlottes = useAppSelector(state => state.gestionFlotte.entities);
  const voitureEntity = useAppSelector(state => state.voiture.entity);
  const loading = useAppSelector(state => state.voiture.loading);
  const updating = useAppSelector(state => state.voiture.updating);
  const updateSuccess = useAppSelector(state => state.voiture.updateSuccess);

  const handleClose = () => {
    navigate('/voiture');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getGestionFlottes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...voitureEntity,
      ...values,
      gestionFlotte: gestionFlottes.find(it => it.id.toString() === values.gestionFlotte.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...voitureEntity,
          gestionFlotte: voitureEntity?.gestionFlotte?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="reservaionApp.voiture.home.createOrEditLabel" data-cy="VoitureCreateUpdateHeading">
            <Translate contentKey="reservaionApp.voiture.home.createOrEditLabel">Create or edit a Voiture</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="voiture-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('reservaionApp.voiture.marque')}
                id="voiture-marque"
                name="marque"
                data-cy="marque"
                type="text"
              />
              <ValidatedField
                label={translate('reservaionApp.voiture.modele')}
                id="voiture-modele"
                name="modele"
                data-cy="modele"
                type="text"
              />
              <ValidatedField
                label={translate('reservaionApp.voiture.description')}
                id="voiture-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('reservaionApp.voiture.disponibilite')}
                id="voiture-disponibilite"
                name="disponibilite"
                data-cy="disponibilite"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('reservaionApp.voiture.tarifJournalier')}
                id="voiture-tarifJournalier"
                name="tarifJournalier"
                data-cy="tarifJournalier"
                type="text"
              />
              <ValidatedField
                id="voiture-gestionFlotte"
                name="gestionFlotte"
                data-cy="gestionFlotte"
                label={translate('reservaionApp.voiture.gestionFlotte')}
                type="select"
              >
                <option value="" key="0" />
                {gestionFlottes
                  ? gestionFlottes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/voiture" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default VoitureUpdate;
