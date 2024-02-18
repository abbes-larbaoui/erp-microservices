import { Module } from '@nestjs/common';
import { AuditLogModule } from './audit-log/audit-log.module';
import { MongooseModule } from '@nestjs/mongoose';
import { EurekaModule } from 'nestjs-eureka';
import { ConfigModule } from '@nestjs/config';

@Module({
  imports: [
    ConfigModule.forRoot({
      envFilePath: `.env.${process.env.NODE_ENV}`,
    }),
    AuditLogModule,
    MongooseModule.forRoot(process.env.MONGO_URI, {
      dbName: process.env.DB_NAME,
    }),
    EurekaModule.forRoot({
      disable: false,
      disableDiscovery: false,
      eureka: {
        host: process.env.EUREKA_HOST,
        port: Number(process.env.EUREKA_PORT),
        servicePath: process.env.EUREKA_SERVICE_PATH,
        maxRetries: Number(process.env.EUREKA_MAX_RETRIES),
        requestRetryDelay: Number(process.env.EUREKA_REQUEST_RETRY_DELAY),
      },
      service: {
        name: process.env.SERVICE_NAME,
        port: Number(process.env.SERVICE_PORT),
      },
    }),
  ],
})
export class AppModule {}
