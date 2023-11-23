import React, { useState } from 'react';
import AvatarEditor from 'react-avatar-editor';
import {
    Button,
    Slider,
    SliderFilledTrack,
    SliderThumb,
    SliderTrack,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    ModalFooter, Box
} from "@chakra-ui/react";

const AvatarEditorWrapper = ({ onClose, onSubmit, image }) => {
    const [zoom, setZoom] = useState(1,5);
    const editor = React.useRef(null);

    const handleSave = () => {
        const canvas = editor.current.getImage();
        const canvasScaled = editor.current.getImageScaledToCanvas();
        onSubmit(canvas.toDataURL(), canvasScaled.toDataURL());
    }

    return (
        <Modal isOpen={true} onClose={onClose} isCentered size="xl">
            <ModalOverlay backdropFilter="blur(10px)" />
            <ModalContent>
                <ModalHeader>Customize the profile image</ModalHeader>
                <ModalCloseButton />
                <ModalBody display="flex" flexDirection="column" alignItems="center" justifyContent="center">
                    <Box position="relative" display="inline-block">
                        <AvatarEditor
                            ref={editor}
                            image={image}
                            width={390}
                            height={390}
                            border={50}
                            borderRadius={195}
                            color={[255, 255, 255, 0.6]}
                            scale={zoom}
                            style={{ margin: 'auto' }}
                        />

                    </Box>
                    <Slider
                        aria-label="zoom"
                        mt={4}
                        w="85%"
                        value={zoom}
                        min={1}
                        max={2}
                        step={0.01}
                        onChange={(value) => setZoom(value)}
                        focusThumbOnChange={false}
                    >
                        <SliderTrack>
                            <SliderFilledTrack bg="green.200" />
                        </SliderTrack>
                        <SliderThumb boxSize={6} bg="green.500" />
                    </Slider>

                </ModalBody>
                <ModalFooter>
                    <Button
                        fontSize="sm"
                        rounded="full"
                        bg="green.400"
                        color="white"
                        _hover={{
                            bg: 'green.500',
                        }}
                        _focus={{
                            bg: 'green.500',
                        }}
                        mr={2}
                        onClick={handleSave}
                    >
                        Save
                    </Button>
                    <Button
                        fontSize="sm"
                        rounded="full"
                        bg="red.500"
                        color="white"
                        _hover={{
                            bg: 'red.600',
                        }}
                        _focus={{
                            bg: 'red.600',
                        }}
                        onClick={onClose}
                    >
                        Cancel
                    </Button>
                </ModalFooter>
            </ModalContent>
        </Modal>
    );
};

export default AvatarEditorWrapper;
