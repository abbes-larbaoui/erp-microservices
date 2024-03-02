import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document } from 'mongoose';

export type AuditLogDocument = AuditLog & Document;

@Schema()
export class AuditLog extends Document {
  @Prop()
  action: string;

  @Prop()
  utilisateur: string;

  @Prop()
  ipAddress: string;

  @Prop()
  entityName: string;

  @Prop()
  entityId: number;

  @Prop()
  moduleName: string;

  @Prop()
  data: string;

  @Prop()
  date: Date;
}

export const AuditLogSchema = SchemaFactory.createForClass(AuditLog);
