# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: pingpong.proto
# Protobuf Python Version: 5.29.0
"""Generated protocol buffer code."""
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import runtime_version as _runtime_version
from google.protobuf import symbol_database as _symbol_database
from google.protobuf.internal import builder as _builder
_runtime_version.ValidateProtobufRuntimeVersion(
    _runtime_version.Domain.PUBLIC,
    5,
    29,
    0,
    '',
    'pingpong.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x0epingpong.proto\"\"\n\x0fPingPongRequest\x12\x0f\n\x07message\x18\x01 \x01(\t\"#\n\x10PingPongResponse\x12\x0f\n\x07message\x18\x01 \x01(\t2A\n\x0fPingPongService\x12.\n\x07getPong\x12\x10.PingPongRequest\x1a\x11.PingPongResponseB1\n-com.advancedbackend.module_two.stubs.pingpongP\x01\x62\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'pingpong_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'\n-com.advancedbackend.module_two.stubs.pingpongP\001'
  _globals['_PINGPONGREQUEST']._serialized_start=18
  _globals['_PINGPONGREQUEST']._serialized_end=52
  _globals['_PINGPONGRESPONSE']._serialized_start=54
  _globals['_PINGPONGRESPONSE']._serialized_end=89
  _globals['_PINGPONGSERVICE']._serialized_start=91
  _globals['_PINGPONGSERVICE']._serialized_end=156
# @@protoc_insertion_point(module_scope)
