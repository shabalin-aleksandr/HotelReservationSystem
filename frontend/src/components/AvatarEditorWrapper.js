import React, { useState } from 'react';
import AvatarEditor from 'react-avatar-editor';
import {
    Box,
    Button,
    Text,
    Slider,
    SliderFilledTrack,
    SliderThumb,
    SliderTrack, Stack, ButtonGroup
} from "@chakra-ui/react";

const AvatarEditorWrapper = ({ onClose, onSubmit, image }) => {
    const [zoom, setZoom] = useState(1);
    const editor = React.useRef(null);

    const handleSave = () => {
        const canvas = editor.current.getImage();
        const canvasScaled = editor.current.getImageScaledToCanvas();
        onSubmit(canvas.toDataURL(), canvasScaled.toDataURL());
    }

    return (
        <Box>
            <Box
                display="flex"
                flexDirection="column"
                alignItems="center"
                justifyContent="center"
                mb={2}
            >
                <Text fontSize='20px' align='center' mb={2} fontWeight='bold'>Customize the profile image</Text>
                <AvatarEditor
                    ref={editor}
                    image={image}
                    width={250}
                    height={250}
                    border={50}
                    borderRadius={500}
                    color={[255, 255, 255, 0.6]}
                    scale={zoom}
                    align='center'
                />
                <Slider
                    mt={2}
                    aria-label="zoom"
                    min={1}
                    max={3}
                    step={0.01}
                    defaultValue={1.2}
                    onChange={(value) => setZoom(value)}
                >
                    <SliderTrack bg='green.100'>
                        <SliderFilledTrack bg='green'/>
                    </SliderTrack>
                    <SliderThumb />
                </Slider>
            </Box>
            <Stack direction='column'>
                <Box
                    display='flex'
                    alignItems='center'
                    justifyContent='right'
                >
                    <ButtonGroup>
                        <Button onClick={handleSave} colorScheme='green'>Save</Button>
                        <Button onClick={onClose} colorScheme='red' variant='outline'>Cancel</Button>
                    </ButtonGroup>
                </Box>
            </Stack>
        </Box>
    );
};

export default AvatarEditorWrapper;