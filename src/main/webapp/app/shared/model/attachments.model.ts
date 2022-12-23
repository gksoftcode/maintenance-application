import { IWorkOrdersHistory } from '@/shared/model/work-orders-history.model';

export interface IAttachments {
  id?: number;
  attachName?: string | null;
  attachContentType?: string | null;
  attach?: string | null;
  workOrdersHistory?: IWorkOrdersHistory | null;
}

export class Attachments implements IAttachments {
  constructor(
    public id?: number,
    public attachName?: string | null,
    public attachContentType?: string | null,
    public attach?: string | null,
    public workOrdersHistory?: IWorkOrdersHistory | null
  ) {}
}
