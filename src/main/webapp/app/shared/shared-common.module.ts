import { NgModule } from '@angular/core';

import { MovieDiarySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [MovieDiarySharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [MovieDiarySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class MovieDiarySharedCommonModule {}
