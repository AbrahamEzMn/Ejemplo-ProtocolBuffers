///
//  Generated code. Do not modify.
//  source: phoneNumber.proto
//
// @dart = 2.3
// ignore_for_file: camel_case_types,non_constant_identifier_names,library_prefixes,unused_import,unused_shown_name,return_of_invalid_type

import 'dart:core' as $core;

import 'package:protobuf/protobuf.dart' as $pb;

import 'phoneType.pbenum.dart' as $0;

class PhoneNumber extends $pb.GeneratedMessage {
  static final $pb.BuilderInfo _i = $pb.BuilderInfo('PhoneNumber', package: const $pb.PackageName('messages'), createEmptyInstance: create)
    ..aOS(1, 'number')
    ..e<$0.PhoneType>(2, 'type', $pb.PbFieldType.OE, defaultOrMaker: $0.PhoneType.MOBILE, valueOf: $0.PhoneType.valueOf, enumValues: $0.PhoneType.values)
    ..hasRequiredFields = false
  ;

  PhoneNumber._() : super();
  factory PhoneNumber() => create();
  factory PhoneNumber.fromBuffer($core.List<$core.int> i, [$pb.ExtensionRegistry r = $pb.ExtensionRegistry.EMPTY]) => create()..mergeFromBuffer(i, r);
  factory PhoneNumber.fromJson($core.String i, [$pb.ExtensionRegistry r = $pb.ExtensionRegistry.EMPTY]) => create()..mergeFromJson(i, r);
  PhoneNumber clone() => PhoneNumber()..mergeFromMessage(this);
  PhoneNumber copyWith(void Function(PhoneNumber) updates) => super.copyWith((message) => updates(message as PhoneNumber));
  $pb.BuilderInfo get info_ => _i;
  @$core.pragma('dart2js:noInline')
  static PhoneNumber create() => PhoneNumber._();
  PhoneNumber createEmptyInstance() => create();
  static $pb.PbList<PhoneNumber> createRepeated() => $pb.PbList<PhoneNumber>();
  static PhoneNumber getDefault() => _defaultInstance ??= create()..freeze();
  static PhoneNumber _defaultInstance;

  $core.String get number => $_getS(0, '');
  set number($core.String v) { $_setString(0, v); }
  $core.bool hasNumber() => $_has(0);
  void clearNumber() => clearField(1);

  $0.PhoneType get type => $_getN(1);
  set type($0.PhoneType v) { setField(2, v); }
  $core.bool hasType() => $_has(1);
  void clearType() => clearField(2);
}

