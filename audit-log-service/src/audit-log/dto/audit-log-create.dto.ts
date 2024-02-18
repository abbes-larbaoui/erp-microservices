export class AuditLogCreateDto {
  action: string;
  userName: string;
  entityName: string;
  entityId: number;
  moduleName: string;
  data: string;
  timestamp: number[];
  date: Date;
}
