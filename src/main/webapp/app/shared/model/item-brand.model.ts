export interface IItemBrand {
  id?: number;
  brandName?: string | null;
}

export class ItemBrand implements IItemBrand {
  constructor(public id?: number, public brandName?: string | null) {}
}
