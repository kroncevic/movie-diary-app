import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDiary } from 'app/shared/model/diary.model';
import { DiaryService } from './diary.service';

@Component({
    selector: 'jhi-diary-update',
    templateUrl: './diary-update.component.html'
})
export class DiaryUpdateComponent implements OnInit {
    diary: IDiary;
    isSaving: boolean;

    constructor(private diaryService: DiaryService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ diary }) => {
            this.diary = diary;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.diary.id !== undefined) {
            this.subscribeToSaveResponse(this.diaryService.update(this.diary));
        } else {
            this.subscribeToSaveResponse(this.diaryService.create(this.diary));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDiary>>) {
        result.subscribe((res: HttpResponse<IDiary>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
