// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: phoneNumber.proto

package messages;

public final class PhoneNumberOuterClass {
  private PhoneNumberOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PhoneNumberOrBuilder extends
      // @@protoc_insertion_point(interface_extends:messages.PhoneNumber)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string number = 1;</code>
     */
    java.lang.String getNumber();
    /**
     * <code>string number = 1;</code>
     */
    com.google.protobuf.ByteString
        getNumberBytes();

    /**
     * <code>.messages.PhoneType type = 2;</code>
     */
    int getTypeValue();
    /**
     * <code>.messages.PhoneType type = 2;</code>
     */
    messages.PhoneTypeOuterClass.PhoneType getType();
  }
  /**
   * Protobuf type {@code messages.PhoneNumber}
   */
  public  static final class PhoneNumber extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:messages.PhoneNumber)
      PhoneNumberOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use PhoneNumber.newBuilder() to construct.
    private PhoneNumber(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private PhoneNumber() {
      number_ = "";
      type_ = 0;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private PhoneNumber(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              number_ = s;
              break;
            }
            case 16: {
              int rawValue = input.readEnum();

              type_ = rawValue;
              break;
            }
            default: {
              if (!parseUnknownFieldProto3(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return messages.PhoneNumberOuterClass.internal_static_messages_PhoneNumber_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return messages.PhoneNumberOuterClass.internal_static_messages_PhoneNumber_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              messages.PhoneNumberOuterClass.PhoneNumber.class, messages.PhoneNumberOuterClass.PhoneNumber.Builder.class);
    }

    public static final int NUMBER_FIELD_NUMBER = 1;
    private volatile java.lang.Object number_;
    /**
     * <code>string number = 1;</code>
     */
    public java.lang.String getNumber() {
      java.lang.Object ref = number_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        number_ = s;
        return s;
      }
    }
    /**
     * <code>string number = 1;</code>
     */
    public com.google.protobuf.ByteString
        getNumberBytes() {
      java.lang.Object ref = number_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        number_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TYPE_FIELD_NUMBER = 2;
    private int type_;
    /**
     * <code>.messages.PhoneType type = 2;</code>
     */
    public int getTypeValue() {
      return type_;
    }
    /**
     * <code>.messages.PhoneType type = 2;</code>
     */
    public messages.PhoneTypeOuterClass.PhoneType getType() {
      @SuppressWarnings("deprecation")
      messages.PhoneTypeOuterClass.PhoneType result = messages.PhoneTypeOuterClass.PhoneType.valueOf(type_);
      return result == null ? messages.PhoneTypeOuterClass.PhoneType.UNRECOGNIZED : result;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getNumberBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, number_);
      }
      if (type_ != messages.PhoneTypeOuterClass.PhoneType.MOBILE.getNumber()) {
        output.writeEnum(2, type_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getNumberBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, number_);
      }
      if (type_ != messages.PhoneTypeOuterClass.PhoneType.MOBILE.getNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(2, type_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof messages.PhoneNumberOuterClass.PhoneNumber)) {
        return super.equals(obj);
      }
      messages.PhoneNumberOuterClass.PhoneNumber other = (messages.PhoneNumberOuterClass.PhoneNumber) obj;

      boolean result = true;
      result = result && getNumber()
          .equals(other.getNumber());
      result = result && type_ == other.type_;
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + NUMBER_FIELD_NUMBER;
      hash = (53 * hash) + getNumber().hashCode();
      hash = (37 * hash) + TYPE_FIELD_NUMBER;
      hash = (53 * hash) + type_;
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static messages.PhoneNumberOuterClass.PhoneNumber parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static messages.PhoneNumberOuterClass.PhoneNumber parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static messages.PhoneNumberOuterClass.PhoneNumber parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static messages.PhoneNumberOuterClass.PhoneNumber parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static messages.PhoneNumberOuterClass.PhoneNumber parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static messages.PhoneNumberOuterClass.PhoneNumber parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static messages.PhoneNumberOuterClass.PhoneNumber parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static messages.PhoneNumberOuterClass.PhoneNumber parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static messages.PhoneNumberOuterClass.PhoneNumber parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static messages.PhoneNumberOuterClass.PhoneNumber parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static messages.PhoneNumberOuterClass.PhoneNumber parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static messages.PhoneNumberOuterClass.PhoneNumber parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(messages.PhoneNumberOuterClass.PhoneNumber prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code messages.PhoneNumber}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:messages.PhoneNumber)
        messages.PhoneNumberOuterClass.PhoneNumberOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return messages.PhoneNumberOuterClass.internal_static_messages_PhoneNumber_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return messages.PhoneNumberOuterClass.internal_static_messages_PhoneNumber_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                messages.PhoneNumberOuterClass.PhoneNumber.class, messages.PhoneNumberOuterClass.PhoneNumber.Builder.class);
      }

      // Construct using messages.PhoneNumberOuterClass.PhoneNumber.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        number_ = "";

        type_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return messages.PhoneNumberOuterClass.internal_static_messages_PhoneNumber_descriptor;
      }

      @java.lang.Override
      public messages.PhoneNumberOuterClass.PhoneNumber getDefaultInstanceForType() {
        return messages.PhoneNumberOuterClass.PhoneNumber.getDefaultInstance();
      }

      @java.lang.Override
      public messages.PhoneNumberOuterClass.PhoneNumber build() {
        messages.PhoneNumberOuterClass.PhoneNumber result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public messages.PhoneNumberOuterClass.PhoneNumber buildPartial() {
        messages.PhoneNumberOuterClass.PhoneNumber result = new messages.PhoneNumberOuterClass.PhoneNumber(this);
        result.number_ = number_;
        result.type_ = type_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return (Builder) super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof messages.PhoneNumberOuterClass.PhoneNumber) {
          return mergeFrom((messages.PhoneNumberOuterClass.PhoneNumber)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(messages.PhoneNumberOuterClass.PhoneNumber other) {
        if (other == messages.PhoneNumberOuterClass.PhoneNumber.getDefaultInstance()) return this;
        if (!other.getNumber().isEmpty()) {
          number_ = other.number_;
          onChanged();
        }
        if (other.type_ != 0) {
          setTypeValue(other.getTypeValue());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        messages.PhoneNumberOuterClass.PhoneNumber parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (messages.PhoneNumberOuterClass.PhoneNumber) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object number_ = "";
      /**
       * <code>string number = 1;</code>
       */
      public java.lang.String getNumber() {
        java.lang.Object ref = number_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          number_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string number = 1;</code>
       */
      public com.google.protobuf.ByteString
          getNumberBytes() {
        java.lang.Object ref = number_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          number_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string number = 1;</code>
       */
      public Builder setNumber(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        number_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string number = 1;</code>
       */
      public Builder clearNumber() {
        
        number_ = getDefaultInstance().getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>string number = 1;</code>
       */
      public Builder setNumberBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        number_ = value;
        onChanged();
        return this;
      }

      private int type_ = 0;
      /**
       * <code>.messages.PhoneType type = 2;</code>
       */
      public int getTypeValue() {
        return type_;
      }
      /**
       * <code>.messages.PhoneType type = 2;</code>
       */
      public Builder setTypeValue(int value) {
        type_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>.messages.PhoneType type = 2;</code>
       */
      public messages.PhoneTypeOuterClass.PhoneType getType() {
        @SuppressWarnings("deprecation")
        messages.PhoneTypeOuterClass.PhoneType result = messages.PhoneTypeOuterClass.PhoneType.valueOf(type_);
        return result == null ? messages.PhoneTypeOuterClass.PhoneType.UNRECOGNIZED : result;
      }
      /**
       * <code>.messages.PhoneType type = 2;</code>
       */
      public Builder setType(messages.PhoneTypeOuterClass.PhoneType value) {
        if (value == null) {
          throw new NullPointerException();
        }
        
        type_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>.messages.PhoneType type = 2;</code>
       */
      public Builder clearType() {
        
        type_ = 0;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFieldsProto3(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:messages.PhoneNumber)
    }

    // @@protoc_insertion_point(class_scope:messages.PhoneNumber)
    private static final messages.PhoneNumberOuterClass.PhoneNumber DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new messages.PhoneNumberOuterClass.PhoneNumber();
    }

    public static messages.PhoneNumberOuterClass.PhoneNumber getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<PhoneNumber>
        PARSER = new com.google.protobuf.AbstractParser<PhoneNumber>() {
      @java.lang.Override
      public PhoneNumber parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new PhoneNumber(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<PhoneNumber> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<PhoneNumber> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public messages.PhoneNumberOuterClass.PhoneNumber getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_messages_PhoneNumber_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_messages_PhoneNumber_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021phoneNumber.proto\022\010messages\032\017phoneType" +
      ".proto\"@\n\013PhoneNumber\022\016\n\006number\030\001 \001(\t\022!\n" +
      "\004type\030\002 \001(\0162\023.messages.PhoneTypeb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          messages.PhoneTypeOuterClass.getDescriptor(),
        }, assigner);
    internal_static_messages_PhoneNumber_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_messages_PhoneNumber_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_messages_PhoneNumber_descriptor,
        new java.lang.String[] { "Number", "Type", });
    messages.PhoneTypeOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
