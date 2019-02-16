// package: messages
// file: person.proto

import * as jspb from "google-protobuf";
import * as phoneNumber_pb from "./phoneNumber_pb";

export class Person extends jspb.Message {
  getName(): string;
  setName(value: string): void;

  getId(): number;
  setId(value: number): void;

  getEmail(): string;
  setEmail(value: string): void;

  clearPhoneList(): void;
  getPhoneList(): Array<phoneNumber_pb.PhoneNumber>;
  setPhoneList(value: Array<phoneNumber_pb.PhoneNumber>): void;
  addPhone(value?: phoneNumber_pb.PhoneNumber, index?: number): phoneNumber_pb.PhoneNumber;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): Person.AsObject;
  static toObject(includeInstance: boolean, msg: Person): Person.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: Person, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): Person;
  static deserializeBinaryFromReader(message: Person, reader: jspb.BinaryReader): Person;
}

export namespace Person {
  export type AsObject = {
    name: string,
    id: number,
    email: string,
    phoneList: Array<phoneNumber_pb.PhoneNumber.AsObject>,
  }
}

