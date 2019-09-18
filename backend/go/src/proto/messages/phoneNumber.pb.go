// Code generated by protoc-gen-go. DO NOT EDIT.
// source: phoneNumber.proto

package messages

import (
	fmt "fmt"
	proto "github.com/golang/protobuf/proto"
	math "math"
)

// Reference imports to suppress errors if they are not otherwise used.
var _ = proto.Marshal
var _ = fmt.Errorf
var _ = math.Inf

// This is a compile-time assertion to ensure that this generated file
// is compatible with the proto package it is being compiled against.
// A compilation error at this line likely means your copy of the
// proto package needs to be updated.
const _ = proto.ProtoPackageIsVersion3 // please upgrade the proto package

type PhoneNumber struct {
	Number               string    `protobuf:"bytes,1,opt,name=number,proto3" json:"number,omitempty"`
	Type                 PhoneType `protobuf:"varint,2,opt,name=type,proto3,enum=messages.PhoneType" json:"type,omitempty"`
	XXX_NoUnkeyedLiteral struct{}  `json:"-"`
	XXX_unrecognized     []byte    `json:"-"`
	XXX_sizecache        int32     `json:"-"`
}

func (m *PhoneNumber) Reset()         { *m = PhoneNumber{} }
func (m *PhoneNumber) String() string { return proto.CompactTextString(m) }
func (*PhoneNumber) ProtoMessage()    {}
func (*PhoneNumber) Descriptor() ([]byte, []int) {
	return fileDescriptor_4f012f699556c771, []int{0}
}

func (m *PhoneNumber) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_PhoneNumber.Unmarshal(m, b)
}
func (m *PhoneNumber) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_PhoneNumber.Marshal(b, m, deterministic)
}
func (m *PhoneNumber) XXX_Merge(src proto.Message) {
	xxx_messageInfo_PhoneNumber.Merge(m, src)
}
func (m *PhoneNumber) XXX_Size() int {
	return xxx_messageInfo_PhoneNumber.Size(m)
}
func (m *PhoneNumber) XXX_DiscardUnknown() {
	xxx_messageInfo_PhoneNumber.DiscardUnknown(m)
}

var xxx_messageInfo_PhoneNumber proto.InternalMessageInfo

func (m *PhoneNumber) GetNumber() string {
	if m != nil {
		return m.Number
	}
	return ""
}

func (m *PhoneNumber) GetType() PhoneType {
	if m != nil {
		return m.Type
	}
	return PhoneType_MOBILE
}

func init() {
	proto.RegisterType((*PhoneNumber)(nil), "messages.PhoneNumber")
}

func init() { proto.RegisterFile("phoneNumber.proto", fileDescriptor_4f012f699556c771) }

var fileDescriptor_4f012f699556c771 = []byte{
	// 117 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0xff, 0xe2, 0x12, 0x2c, 0xc8, 0xc8, 0xcf,
	0x4b, 0xf5, 0x2b, 0xcd, 0x4d, 0x4a, 0x2d, 0xd2, 0x2b, 0x28, 0xca, 0x2f, 0xc9, 0x17, 0xe2, 0xc8,
	0x4d, 0x2d, 0x2e, 0x4e, 0x4c, 0x4f, 0x2d, 0x96, 0xe2, 0x07, 0x4b, 0x86, 0x54, 0x16, 0xa4, 0x42,
	0xa4, 0x94, 0xfc, 0xb8, 0xb8, 0x03, 0x10, 0xea, 0x85, 0xc4, 0xb8, 0xd8, 0xf2, 0xc0, 0x2c, 0x09,
	0x46, 0x05, 0x46, 0x0d, 0xce, 0x20, 0x28, 0x4f, 0x48, 0x9d, 0x8b, 0xa5, 0xa4, 0xb2, 0x20, 0x55,
	0x82, 0x49, 0x81, 0x51, 0x83, 0xcf, 0x48, 0x58, 0x0f, 0x66, 0xa0, 0x5e, 0x00, 0xcc, 0xbc, 0x20,
	0xb0, 0x82, 0x24, 0x36, 0xb0, 0xb1, 0xc6, 0x80, 0x00, 0x00, 0x00, 0xff, 0xff, 0x48, 0x5f, 0x58,
	0x57, 0x86, 0x00, 0x00, 0x00,
}
