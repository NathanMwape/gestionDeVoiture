import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGestionFlotte } from 'app/shared/model/gestion-flotte.model';
import { getEntities } from './gestion-flotte.reducer';

export const GestionFlotte = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const gestionFlotteList = useAppSelector(state => state.gestionFlotte.entities);
  const loading = useAppSelector(state => state.gestionFlotte.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="gestion-flotte-heading" data-cy="GestionFlotteHeading">
        <Translate contentKey="reservaionApp.gestionFlotte.home.title">Gestion Flottes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="reservaionApp.gestionFlotte.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/gestion-flotte/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="reservaionApp.gestionFlotte.home.createLabel">Create new Gestion Flotte</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {gestionFlotteList && gestionFlotteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="reservaionApp.gestionFlotte.id">ID</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {gestionFlotteList.map((gestionFlotte, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/gestion-flotte/${gestionFlotte.id}`} color="link" size="sm">
                      {gestionFlotte.id}
                    </Button>
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/gestion-flotte/${gestionFlotte.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/gestion-flotte/${gestionFlotte.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/gestion-flotte/${gestionFlotte.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="reservaionApp.gestionFlotte.home.notFound">No Gestion Flottes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default GestionFlotte;
