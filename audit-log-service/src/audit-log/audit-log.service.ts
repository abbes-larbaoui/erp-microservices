import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { AuditLog, AuditLogDocument } from './schemas/audit-log.schema';
import { Model } from 'mongoose';
import { AuditLogCreateDto } from './dto/audit-log-create.dto';

@Injectable()
export class AuditLogService {
  constructor(
    @InjectModel(AuditLog.name) private auditLogModel: Model<AuditLogDocument>,
  ) {}

  async create(auditLogCreate: AuditLogCreateDto): Promise<AuditLog> {
    const date = new Date(Date.UTC.apply(null, auditLogCreate.timestamp));
    auditLogCreate.date = date;
    return new this.auditLogModel(auditLogCreate).save();
  }

  async findAll(): Promise<AuditLog[]> {
    return this.auditLogModel.find().exec();
  }

  async findOne(id: string): Promise<AuditLog> {
    return this.auditLogModel.findById(id).exec();
  }
}
