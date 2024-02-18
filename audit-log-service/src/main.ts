import { NestFactory } from '@nestjs/core';
import { Transport } from '@nestjs/microservices';
import { AppModule } from './app.module';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  app.connectMicroservice({
    transport: Transport.KAFKA,
    options: {
      client: {
        brokers: [process.env.BROKER_URI],
      },
      consumer: {
        groupId: process.env.BROKER_CONSUMER_GROUP_ID,
      },
    },
  });

  await app.startAllMicroservices();
  await app.listen(Number(process.env.SERVICE_PORT));
  console.log('Microservice Audit Log is listening');
}
bootstrap();
