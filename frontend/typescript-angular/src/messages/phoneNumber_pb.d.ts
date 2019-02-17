// package: messages
// file: phoneNumber.proto

import * as jspb from "google-protobuf";
import * as phoneType_pb from "./phoneType_pb";

export class PhoneNumber extends jspb.Message {
  getNumber(): string;
  setNumber(value: string): void;

  getType(): phoneType_pb.PhoneType;
  setType(value: phoneType_pb.PhoneType): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): PhoneNumber.AsObject;
  static toObject(includeInstance: boolean, msg: PhoneNumber): PhoneNumber.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: PhoneNumber, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): PhoneNumber;
  static deserializeBinaryFromReader(message: PhoneNumber, reader: jspb.BinaryReader): PhoneNumber;
}

export namespace PhoneNumber {
  export type AsObject = {
    number: string,
    type: phoneType_pb.PhoneType,
  }
}

