import { Controller, Get, Param } from '@nestjs/common';
import { EventPattern, Payload } from '@nestjs/microservices';
import { AuditLogService } from './audit-log.service';

@Controller()
export class AuditLogController {
  constructor(private readonly auditLogService: AuditLogService) {}

  @EventPattern('auditLogTopic')
  handleMyTopicEvent(@Payload() data: any) {
    this.auditLogService.create(data).then(() => console.log('created'));
    console.log(data);
  }

  @Get('/api/v1/all')
  async index() {
    return await this.auditLogService.findAll();
  }

  @Get('/api/v1/:id')
  async find(@Param('id') id: string) {
    return await this.auditLogService.findOne(id);

    // let audit = await this.auditLogService.findOne(id);
    // return JSON.parse(audit.data);
  }
}
