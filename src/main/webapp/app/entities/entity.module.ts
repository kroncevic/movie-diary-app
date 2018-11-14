import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MovieDiaryDiaryModule } from './diary/diary.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        MovieDiaryDiaryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    exports: [MovieDiaryDiaryModule],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieDiaryEntityModule {}
