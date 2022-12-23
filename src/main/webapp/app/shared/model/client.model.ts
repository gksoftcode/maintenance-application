import { IAppintment } from '@/shared/model/appintment.model';

export interface IClient {
  id?: number;
  fullName?: string | null;
  mobileNo?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  appintments?: IAppintment[] | null;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public fullName?: string | null,
    public mobileNo?: string | null,
    public email?: string | null,
    public phoneNumber?: string | null,
    public appintments?: IAppintment[] | null
  ) {}
}
