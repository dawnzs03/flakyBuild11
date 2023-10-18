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

class SQLNotNullConstraint
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'catName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        2 => array(
            'var' => 'table_db',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'table_name',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        4 => array(
            'var' => 'column_name',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        5 => array(
            'var' => 'nn_name',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        6 => array(
            'var' => 'enable_cstr',
            'isRequired' => false,
            'type' => TType::BOOL,
        ),
        7 => array(
            'var' => 'validate_cstr',
            'isRequired' => false,
            'type' => TType::BOOL,
        ),
        8 => array(
            'var' => 'rely_cstr',
            'isRequired' => false,
            'type' => TType::BOOL,
        ),
    );

    /**
     * @var string
     */
    public $catName = null;
    /**
     * @var string
     */
    public $table_db = null;
    /**
     * @var string
     */
    public $table_name = null;
    /**
     * @var string
     */
    public $column_name = null;
    /**
     * @var string
     */
    public $nn_name = null;
    /**
     * @var bool
     */
    public $enable_cstr = null;
    /**
     * @var bool
     */
    public $validate_cstr = null;
    /**
     * @var bool
     */
    public $rely_cstr = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['catName'])) {
                $this->catName = $vals['catName'];
            }
            if (isset($vals['table_db'])) {
                $this->table_db = $vals['table_db'];
            }
            if (isset($vals['table_name'])) {
                $this->table_name = $vals['table_name'];
            }
            if (isset($vals['column_name'])) {
                $this->column_name = $vals['column_name'];
            }
            if (isset($vals['nn_name'])) {
                $this->nn_name = $vals['nn_name'];
            }
            if (isset($vals['enable_cstr'])) {
                $this->enable_cstr = $vals['enable_cstr'];
            }
            if (isset($vals['validate_cstr'])) {
                $this->validate_cstr = $vals['validate_cstr'];
            }
            if (isset($vals['rely_cstr'])) {
                $this->rely_cstr = $vals['rely_cstr'];
            }
        }
    }

    public function getName()
    {
        return 'SQLNotNullConstraint';
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
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->catName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->table_db);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->table_name);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->column_name);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->nn_name);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 6:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->enable_cstr);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 7:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->validate_cstr);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 8:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->rely_cstr);
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
        $xfer += $output->writeStructBegin('SQLNotNullConstraint');
        if ($this->catName !== null) {
            $xfer += $output->writeFieldBegin('catName', TType::STRING, 1);
            $xfer += $output->writeString($this->catName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->table_db !== null) {
            $xfer += $output->writeFieldBegin('table_db', TType::STRING, 2);
            $xfer += $output->writeString($this->table_db);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->table_name !== null) {
            $xfer += $output->writeFieldBegin('table_name', TType::STRING, 3);
            $xfer += $output->writeString($this->table_name);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->column_name !== null) {
            $xfer += $output->writeFieldBegin('column_name', TType::STRING, 4);
            $xfer += $output->writeString($this->column_name);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->nn_name !== null) {
            $xfer += $output->writeFieldBegin('nn_name', TType::STRING, 5);
            $xfer += $output->writeString($this->nn_name);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->enable_cstr !== null) {
            $xfer += $output->writeFieldBegin('enable_cstr', TType::BOOL, 6);
            $xfer += $output->writeBool($this->enable_cstr);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->validate_cstr !== null) {
            $xfer += $output->writeFieldBegin('validate_cstr', TType::BOOL, 7);
            $xfer += $output->writeBool($this->validate_cstr);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->rely_cstr !== null) {
            $xfer += $output->writeFieldBegin('rely_cstr', TType::BOOL, 8);
            $xfer += $output->writeBool($this->rely_cstr);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
