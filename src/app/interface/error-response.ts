import { ErrorDetail } from "./error-detail";

export interface ErrorResponse {
  message: string;
  detailHolders?: ErrorDetail[];
}
