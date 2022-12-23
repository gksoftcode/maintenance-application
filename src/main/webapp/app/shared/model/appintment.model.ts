import { IClient } from '@/shared/model/client.model';

export interface IAppintment {
  id?: number;
  appDate?: Date | null;
  notes?: string | null;
  client?: IClient | null;
}

export class Appintment implements IAppintment {
  constructor(public id?: number, public appDate?: Date | null, public notes?: string | null, public client?: IClient | null) {}
}
