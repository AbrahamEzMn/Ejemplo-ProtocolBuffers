///
//  Generated code. Do not modify.
//  source: person.proto
//
// @dart = 2.3
// ignore_for_file: camel_case_types,non_constant_identifier_names,library_prefixes,unused_import,unused_shown_name,return_of_invalid_type

import 'dart:core' as $core;

import 'package:protobuf/protobuf.dart' as $pb;

import 'phoneNumber.pb.dart' as $1;

class Person extends $pb.GeneratedMessage {
  static final $pb.BuilderInfo _i = $pb.BuilderInfo('Person', package: const $pb.PackageName('messages'), createEmptyInstance: create)
    ..aOS(1, 'name')
    ..a<$core.int>(2, 'id', $pb.PbFieldType.O3)
    ..aOS(3, 'email')
    ..pc<$1.PhoneNumber>(4, 'phone', $pb.PbFieldType.PM, subBuilder: $1.PhoneNumber.create)
    ..hasRequiredFields = false
  ;

  Person._() : super();
  factory Person() => create();
  factory Person.fromBuffer($core.List<$core.int> i, [$pb.ExtensionRegistry r = $pb.ExtensionRegistry.EMPTY]) => create()..mergeFromBuffer(i, r);
  factory Person.fromJson($core.String i, [$pb.ExtensionRegistry r = $pb.ExtensionRegistry.EMPTY]) => create()..mergeFromJson(i, r);
  Person clone() => Person()..mergeFromMessage(this);
  Person copyWith(void Function(Person) updates) => super.copyWith((message) => updates(message as Person));
  $pb.BuilderInfo get info_ => _i;
  @$core.pragma('dart2js:noInline')
  static Person create() => Person._();
  Person createEmptyInstance() => create();
  static $pb.PbList<Person> createRepeated() => $pb.PbList<Person>();
  static Person getDefault() => _defaultInstance ??= create()..freeze();
  static Person _defaultInstance;

  $core.String get name => $_getS(0, '');
  set name($core.String v) { $_setString(0, v); }
  $core.bool hasName() => $_has(0);
  void clearName() => clearField(1);

  $core.int get id => $_get(1, 0);
  set id($core.int v) { $_setSignedInt32(1, v); }
  $core.bool hasId() => $_has(1);
  void clearId() => clearField(2);

  $core.String get email => $_getS(2, '');
  set email($core.String v) { $_setString(2, v); }
  $core.bool hasEmail() => $_has(2);
  void clearEmail() => clearField(3);

  $core.List<$1.PhoneNumber> get phone => $_getList(3);
}

