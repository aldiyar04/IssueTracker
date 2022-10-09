import { HttpErrorResponse, HttpStatusCode } from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { ErrorResponse } from "./interface/error-response";

export class Utils {
  static handleErrorResponse(error: HttpErrorResponse): Observable<never> {
    let errMsg = Utils.getErrorMessage(error);
    return throwError(() => new Error(errMsg));
  }

  private static getErrorMessage(error: HttpErrorResponse): string {
    let errMsg = '';
    if (error.status === HttpStatusCode.Forbidden || error.status === HttpStatusCode.Unauthorized) {
      errMsg = 'Access denied';
    } else {
      let errBody = <ErrorResponse> error.error;
      errMsg = errBody.detailHolders[0].message;
      errBody.detailHolders.splice(1).forEach(detail => {
        errMsg += `. ${detail.message}`;
      });
    }
    return errMsg;
  }
}
