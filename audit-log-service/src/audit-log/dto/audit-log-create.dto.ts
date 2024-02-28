export class AuditLogCreateDto {
  action: string;
  utilisateur: string;
  entityName: string;
  entityId: number;
  moduleName: string;
  data: string;
  timestamp: number[];
  date: Date;
}
