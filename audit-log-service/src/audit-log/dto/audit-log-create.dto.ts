export class AuditLogCreateDto {
  action: string;
  utilisateur: string;
  ipAddress: string;
  entityName: string;
  entityId: number;
  moduleName: string;
  data: string;
  timestamp: number[];
  date: Date;
}
