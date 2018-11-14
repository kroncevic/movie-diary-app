import { Route } from '@angular/router';

import { HomeComponent } from './';
import { DiaryComponent } from 'app/entities/diary';
import { UserRouteAccessService } from 'app/core';

// export const HOME_ROUTE: Route = {
//     path: '',
//     component: HomeComponent,
//     data: {
//         authorities: [],
//         pageTitle: 'Movie Diary'
//     }
// };
export const HOME_ROUTE: Route = {
    path: '',  redirectTo: 'diary', pathMatch: 'full'
};
