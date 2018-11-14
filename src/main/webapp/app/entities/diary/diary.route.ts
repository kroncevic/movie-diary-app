import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Diary } from 'app/shared/model/diary.model';
import { DiaryService } from './diary.service';
import { DiaryComponent } from './diary.component';
import { DiaryDetailComponent } from './diary-detail.component';
import { DiaryUpdateComponent } from './diary-update.component';
import { DiaryDeletePopupComponent } from './diary-delete-dialog.component';
import { IDiary } from 'app/shared/model/diary.model';

@Injectable({ providedIn: 'root' })
export class DiaryResolve implements Resolve<IDiary> {
    constructor(private service: DiaryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Diary> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Diary>) => response.ok),
                map((diary: HttpResponse<Diary>) => diary.body)
            );
        }
        return of(new Diary());
    }
}

export const diaryRoute: Routes = [
    {
        path: 'diary',
        component: DiaryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            // authorities: ['ROLE_ANONYMOUS'],
            defaultSort: 'id,asc',
            pageTitle: 'Diaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'diary/:id/view',
        component: DiaryDetailComponent,
        resolve: {
            diary: DiaryResolve
        },
        data: {
            // authorities: ['ROLE_ANONYMOUS'],
            pageTitle: 'Diaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'diary/new',
        component: DiaryUpdateComponent,
        resolve: {
            diary: DiaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'diary/:id/edit',
        component: DiaryUpdateComponent,
        resolve: {
            diary: DiaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diaries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const diaryPopupRoute: Routes = [
    {
        path: 'diary/:id/delete',
        component: DiaryDeletePopupComponent,
        resolve: {
            diary: DiaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
