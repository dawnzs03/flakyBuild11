<?php
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

class Adjacency
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'node',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        2 => array(
            'var' => 'children',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRING,
            'elem' => array(
                'type' => TType::STRING,
                ),
        ),
        3 => array(
            'var' => 'adjacencyType',
            'isRequired' => false,
            'type' => TType::I32,
            'class' => '\AdjacencyType',
        ),
    );

    /**
     * @var string
     */
    public $node = null;
    /**
     * @var string[]
     */
    public $children = null;
    /**
     * @var int
     */
    public $adjacencyType = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['node'])) {
                $this->node = $vals['node'];
            }
            if (isset($vals['children'])) {
                $this->children = $vals['children'];
            }
            if (isset($vals['adjacencyType'])) {
                $this->adjacencyType = $vals['adjacencyType'];
            }
        }
    }

    public function getName()
    {
        return 'Adjacency';
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
                        $xfer += $input->readString($this->node);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::LST) {
                        $this->children = array();
                        $_size0 = 0;
                        $_etype3 = 0;
                        $xfer += $input->readListBegin($_etype3, $_size0);
                        for ($_i4 = 0; $_i4 < $_size0; ++$_i4) {
                            $elem5 = null;
                            $xfer += $input->readString($elem5);
                            $this->children []= $elem5;
                        }
                        $xfer += $input->readListEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->adjacencyType);
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
        $xfer += $output->writeStructBegin('Adjacency');
        if ($this->node !== null) {
            $xfer += $output->writeFieldBegin('node', TType::STRING, 1);
            $xfer += $output->writeString($this->node);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->children !== null) {
            if (!is_array($this->children)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('children', TType::LST, 2);
            $output->writeListBegin(TType::STRING, count($this->children));
            foreach ($this->children as $iter6) {
                $xfer += $output->writeString($iter6);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->adjacencyType !== null) {
            $xfer += $output->writeFieldBegin('adjacencyType', TType::I32, 3);
            $xfer += $output->writeI32($this->adjacencyType);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
