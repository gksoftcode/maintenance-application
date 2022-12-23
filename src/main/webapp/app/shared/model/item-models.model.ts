export interface IItemModels {
  id?: number;
  modelName?: string | null;
}

export class ItemModels implements IItemModels {
  constructor(public id?: number, public modelName?: string | null) {}
}
