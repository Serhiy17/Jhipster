import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import { JhipsterAdminModule } from '../../admin/admin.module';
import {
    UserSettingService,
    UserSettingPopupService,
    UserSettingComponent,
    UserSettingDetailComponent,
    UserSettingDialogComponent,
    UserSettingPopupComponent,
    UserSettingDeletePopupComponent,
    UserSettingDeleteDialogComponent,
    userSettingRoute,
    userSettingPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userSettingRoute,
    ...userSettingPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        JhipsterAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserSettingComponent,
        UserSettingDetailComponent,
        UserSettingDialogComponent,
        UserSettingDeleteDialogComponent,
        UserSettingPopupComponent,
        UserSettingDeletePopupComponent,
    ],
    entryComponents: [
        UserSettingComponent,
        UserSettingDialogComponent,
        UserSettingPopupComponent,
        UserSettingDeleteDialogComponent,
        UserSettingDeletePopupComponent,
    ],
    providers: [
        UserSettingService,
        UserSettingPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterUserSettingModule {}
