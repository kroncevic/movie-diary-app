import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MovieDiarySharedModule } from 'app/shared';
import {
    DiaryComponent,
    DiaryDetailComponent,
    DiaryUpdateComponent,
    DiaryDeletePopupComponent,
    DiaryDeleteDialogComponent,
    diaryRoute,
    diaryPopupRoute
} from './';

const ENTITY_STATES = [...diaryRoute, ...diaryPopupRoute];

@NgModule({
    imports: [MovieDiarySharedModule, RouterModule.forChild(ENTITY_STATES)],
    exports: [DiaryComponent],
    declarations: [DiaryComponent, DiaryDetailComponent, DiaryUpdateComponent, DiaryDeleteDialogComponent, DiaryDeletePopupComponent],
    entryComponents: [DiaryComponent, DiaryUpdateComponent, DiaryDeleteDialogComponent, DiaryDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieDiaryDiaryModule {}
