export interface IStatus {
  id?: number;
  statusName?: string | null;
}

export class Status implements IStatus {
  constructor(public id?: number, public statusName?: string | null) {}
}
