export interface IActions {
  id?: number;
  actionName?: string | null;
}

export class Actions implements IActions {
  constructor(public id?: number, public actionName?: string | null) {}
}
