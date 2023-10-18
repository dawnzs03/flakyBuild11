<?php
namespace metastore;

/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
use Thrift\Base\TBase;
use Thrift\Type\TType;
use Thrift\Type\TMessageType;
use Thrift\Exception\TException;
use Thrift\Exception\TProtocolException;
use Thrift\Protocol\TProtocol;
use Thrift\Protocol\TBinaryProtocolAccelerated;
use Thrift\Exception\TApplicationException;

class ShowCompactRequest
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'id',
            'isRequired' => false,
            'type' => TType::I64,
        ),
        2 => array(
            'var' => 'poolName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'dbName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        4 => array(
            'var' => 'tbName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        5 => array(
            'var' => 'partName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        6 => array(
            'var' => 'type',
            'isRequired' => false,
            'type' => TType::I32,
            'class' => '\metastore\CompactionType',
        ),
        7 => array(
            'var' => 'state',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        8 => array(
            'var' => 'limit',
            'isRequired' => false,
            'type' => TType::I64,
        ),
        9 => array(
            'var' => 'order',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
    );

    /**
     * @var int
     */
    public $id = null;
    /**
     * @var string
     */
    public $poolName = null;
    /**
     * @var string
     */
    public $dbName = null;
    /**
     * @var string
     */
    public $tbName = null;
    /**
     * @var string
     */
    public $partName = null;
    /**
     * @var int
     */
    public $type = null;
    /**
     * @var string
     */
    public $state = null;
    /**
     * @var int
     */
    public $limit = null;
    /**
     * @var string
     */
    public $order = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['id'])) {
                $this->id = $vals['id'];
            }
            if (isset($vals['poolName'])) {
                $this->poolName = $vals['poolName'];
            }
            if (isset($vals['dbName'])) {
                $this->dbName = $vals['dbName'];
            }
            if (isset($vals['tbName'])) {
                $this->tbName = $vals['tbName'];
            }
            if (isset($vals['partName'])) {
                $this->partName = $vals['partName'];
            }
            if (isset($vals['type'])) {
                $this->type = $vals['type'];
            }
            if (isset($vals['state'])) {
                $this->state = $vals['state'];
            }
            if (isset($vals['limit'])) {
                $this->limit = $vals['limit'];
            }
            if (isset($vals['order'])) {
                $this->order = $vals['order'];
            }
        }
    }

    public function getName()
    {
        return 'ShowCompactRequest';
    }


    public function read($input)
    {
        $xfer = 0;
        $fname = null;
        $ftype = 0;
        $fid = 0;
        $xfer += $input->readStructBegin($fname);
        while (true) {
            $xfer += $input->readFieldBegin($fname, $ftype, $fid);
            if ($ftype == TType::STOP) {
                break;
            }
            switch ($fid) {
                case 1:
                    if ($ftype == TType::I64) {
                        $xfer += $input->readI64($this->id);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->poolName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->dbName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->tbName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->partName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 6:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->type);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 7:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->state);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 8:
                    if ($ftype == TType::I64) {
                        $xfer += $input->readI64($this->limit);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 9:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->order);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                default:
                    $xfer += $input->skip($ftype);
                    break;
            }
            $xfer += $input->readFieldEnd();
        }
        $xfer += $input->readStructEnd();
        return $xfer;
    }

    public function write($output)
    {
        $xfer = 0;
        $xfer += $output->writeStructBegin('ShowCompactRequest');
        if ($this->id !== null) {
            $xfer += $output->writeFieldBegin('id', TType::I64, 1);
            $xfer += $output->writeI64($this->id);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->poolName !== null) {
            $xfer += $output->writeFieldBegin('poolName', TType::STRING, 2);
            $xfer += $output->writeString($this->poolName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->dbName !== null) {
            $xfer += $output->writeFieldBegin('dbName', TType::STRING, 3);
            $xfer += $output->writeString($this->dbName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->tbName !== null) {
            $xfer += $output->writeFieldBegin('tbName', TType::STRING, 4);
            $xfer += $output->writeString($this->tbName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->partName !== null) {
            $xfer += $output->writeFieldBegin('partName', TType::STRING, 5);
            $xfer += $output->writeString($this->partName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->type !== null) {
            $xfer += $output->writeFieldBegin('type', TType::I32, 6);
            $xfer += $output->writeI32($this->type);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->state !== null) {
            $xfer += $output->writeFieldBegin('state', TType::STRING, 7);
            $xfer += $output->writeString($this->state);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->limit !== null) {
            $xfer += $output->writeFieldBegin('limit', TType::I64, 8);
            $xfer += $output->writeI64($this->limit);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->order !== null) {
            $xfer += $output->writeFieldBegin('order', TType::STRING, 9);
            $xfer += $output->writeString($this->order);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
