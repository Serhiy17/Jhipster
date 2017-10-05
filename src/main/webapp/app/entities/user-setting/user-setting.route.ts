import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserSettingComponent } from './user-setting.component';
import { UserSettingDetailComponent } from './user-setting-detail.component';
import { UserSettingPopupComponent } from './user-setting-dialog.component';
import { UserSettingDeletePopupComponent } from './user-setting-delete-dialog.component';

export const userSettingRoute: Routes = [
    {
        path: 'user-setting',
        component: UserSettingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserSettings'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-setting/:id',
        component: UserSettingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserSettings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userSettingPopupRoute: Routes = [
    {
        path: 'user-setting-new',
        component: UserSettingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserSettings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-setting/:id/edit',
        component: UserSettingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserSettings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-setting/:id/delete',
        component: UserSettingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserSettings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
