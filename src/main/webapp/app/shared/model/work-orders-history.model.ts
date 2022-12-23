import { IStatus } from '@/shared/model/status.model';
import { IActions } from '@/shared/model/actions.model';
import { IAttachments } from '@/shared/model/attachments.model';
import { IWorkOrders } from '@/shared/model/work-orders.model';

export interface IWorkOrdersHistory {
  id?: number;
  isShared?: boolean | null;
  actionDate?: Date | null;
  note?: string | null;
  status?: IStatus | null;
  action?: IActions | null;
  attachments?: IAttachments[] | null;
  workOrders?: IWorkOrders | null;
}

export class WorkOrdersHistory implements IWorkOrdersHistory {
  constructor(
    public id?: number,
    public isShared?: boolean | null,
    public actionDate?: Date | null,
    public note?: string | null,
    public status?: IStatus | null,
    public action?: IActions | null,
    public attachments?: IAttachments[] | null,
    public workOrders?: IWorkOrders | null
  ) {
    this.isShared = this.isShared ?? false;
  }
}
