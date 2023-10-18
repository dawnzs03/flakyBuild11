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

class AddDefaultConstraintRequest
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'defaultConstraintCols',
            'isRequired' => true,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\SQLDefaultConstraint',
                ),
        ),
    );

    /**
     * @var \metastore\SQLDefaultConstraint[]
     */
    public $defaultConstraintCols = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['defaultConstraintCols'])) {
                $this->defaultConstraintCols = $vals['defaultConstraintCols'];
            }
        }
    }

    public function getName()
    {
        return 'AddDefaultConstraintRequest';
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
                    if ($ftype == TType::LST) {
                        $this->defaultConstraintCols = array();
                        $_size488 = 0;
                        $_etype491 = 0;
                        $xfer += $input->readListBegin($_etype491, $_size488);
                        for ($_i492 = 0; $_i492 < $_size488; ++$_i492) {
                            $elem493 = null;
                            $elem493 = new \metastore\SQLDefaultConstraint();
                            $xfer += $elem493->read($input);
                            $this->defaultConstraintCols []= $elem493;
                        }
                        $xfer += $input->readListEnd();
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
        $xfer += $output->writeStructBegin('AddDefaultConstraintRequest');
        if ($this->defaultConstraintCols !== null) {
            if (!is_array($this->defaultConstraintCols)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('defaultConstraintCols', TType::LST, 1);
            $output->writeListBegin(TType::STRUCT, count($this->defaultConstraintCols));
            foreach ($this->defaultConstraintCols as $iter494) {
                $xfer += $iter494->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
